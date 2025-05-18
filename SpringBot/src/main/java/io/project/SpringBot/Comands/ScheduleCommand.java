package io.project.SpringBot.Comands;

import io.project.SpringBot.Data.Schedule;
import io.project.SpringBot.Interfaces.BotCommand;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class ScheduleCommand implements BotCommand {
    @Autowired
    private Schedule schedule;

    @Autowired
    private Schedule.OuterObject object;

    private String weekParity;
    private String formattedDate;

    @Override
    public SendMessage getMessage(Update update) {
        String day = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        weekParity = getWeekParity();
        StringBuilder msgText = buildScheduleText(day);
        for (var item : object.getInnerObjects()) {
            String type = item.type();
            String value = item.value();

            if (type.contains("any")) {
                msgText.append(value).append("\n\n");
            } else if (type.contains(formattedDate)) {
                List<String> group1Dates = Arrays.asList(type.split("\\|")[0].trim().split(" "));
                msgText.append(value).append("\n")
                        .append(group1Dates.contains(formattedDate) ? "Идет первая подгруппа" : "Идет вторая подгруппа")
                        .append("\n\n");
            } else if (value.contains("лаба") && type.contains("nechet/chet")) {
                msgText.append(value).append("\n")
                        .append(weekParity.equals("chet") ? "Идет вторая подгруппа" : "Идет первая подгруппа")
                        .append("\n\n");
            } else if (type.equals(weekParity)) {
                msgText.append(value).append("\n\n");
            }
        }

        return SendMessage.builder().chatId(String.valueOf(chatId)).text(msgText.toString()).build();
    }

    private String getWeekParity() {
        try {
            String text = Jsoup.connect("https://xn--d1ababprchchy.xn--p1ai/")
                    .get()
                    .getElementById("ugenr")
                    .text()
                    .substring(7);
            return Integer.parseInt(text) % 2 == 0 ? "chet" : "nechet";
        } catch (IOException e) {
            throw new RuntimeException("Не удалось получить четность недели", e);
        }
    }

    private StringBuilder buildScheduleText(String day) {
        LocalDate today = LocalDate.now();
        DayOfWeek todayDow = today.getDayOfWeek();
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM");

        String target = day.substring(11).toLowerCase();
        boolean isToday = day.contains("today") || target.equals(todayDow.toString().toLowerCase());

        if (isToday) {
            object = schedule.getSchedule().get(todayDow.toString().toLowerCase());
            formattedDate = today.format(fmt);
            sb.append("Расписание на ").append(todayDow).append("\n\n");
        } else {
            DayOfWeek targetDow = DayOfWeek.valueOf(target.toUpperCase());
            LocalDate targetDate = today.with(TemporalAdjusters.next(targetDow));
            formattedDate = targetDate.format(fmt);
            object = schedule.getSchedule().get(target);
            sb.append("Расписание на ").append(targetDow).append("\n\n");

            if (targetDate.isAfter(today.plusDays(7 - todayDow.getValue())))
                weekParity = weekParity.equals("chet") ? "nechet" : "chet";
        }

        return sb;
    }
}
