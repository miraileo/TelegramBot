package io.project.SpringBot.Comands;

import io.project.SpringBot.Data.Raspisanie;
import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.MYAmazingBot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

public class PhotoCommand implements BotCommand {
    private final MYAmazingBot bot;
    private final Raspisanie raspisanie;
    private Raspisanie.OuterObject object;
    private LocalDate currentDate;
    private DayOfWeek dayOfWeek;
    private DateTimeFormatter formatter;
    private String formattedDate;
    private final StringBuilder builder = new StringBuilder();
    private String day;
    private String weekParity;
    public void setDay(String day) {
        this.day = day;
    }
    public PhotoCommand(MYAmazingBot bot, Raspisanie raspisanie) {
        this.bot = bot;
        this.raspisanie = raspisanie;
    }
    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        currentDate = LocalDate.now();
        dayOfWeek = currentDate.getDayOfWeek();
        formatter = DateTimeFormatter.ofPattern("dd.MM");
        setWeekParity();
        setInput();
        for (int i = 0; i < object.getInnerObjects().size(); i++){
            if(object.getInnerObjects().get(i).getType().contains("any")){
                builder.append(object.getInnerObjects().get(i).getValue());
                builder.append("\n\n");
            }
            else if(object.getInnerObjects().get(i).getType().contains(formattedDate)) {
                String[] groups = object.getInnerObjects().get(i).getType().split("\\|");
                List<String> group1Dates = Arrays.asList(groups[0].trim().split(" "));
                builder.append(object.getInnerObjects().get(i).getValue());
                if(group1Dates.contains(formattedDate)) builder.append("\nИдет первая подгруппа");
                else builder.append("\nИдет вторая подгруппа");
                builder.append("\n\n");
            }
            else {
                if(object.getInnerObjects().get(i).getValue().contains("лаба") && object.getInnerObjects().get(i).getType().contains("nechet/chet")){
                    builder.append(object.getInnerObjects().get(i).getValue());
                    if(weekParity.equals("chet")) builder.append("\nИдет вторая подгруппа");
                    else builder.append("\nИдет первая подгруппа");
                    builder.append("\n\n");
                }
                else if(object.getInnerObjects().get(i).getType().equals(weekParity)){
                    builder.append(object.getInnerObjects().get(i).getValue());
                    builder.append("\n\n");
                }
            }
        }
        System.out.println(formattedDate);
        String result = builder.toString();
        builder.setLength(0);
        sendMessage.setText(result);
        bot.execute(sendMessage);
    }

    private void setWeekParity(){
        try {
            Document document = Jsoup.connect("https://xn--d1ababprchchy.xn--p1ai/").get();
            Element weekParityElement = document.getElementById("ugenr");
            weekParity = weekParityElement.text();
            weekParity = weekParity.substring(7);
            if(Integer.parseInt(weekParity)%2==0) weekParity = "chet";
            else weekParity = "nechet";
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке страницы: " + e.getMessage());
        }
    }

    private void setInput(){
        if(day.contains("today") || day.substring(11).equals(dayOfWeek.toString().toLowerCase())){
            builder.append("Расписание на " + dayOfWeek + "\n\n");
            object = raspisanie.getRaspisanie().get(dayOfWeek.toString().toLowerCase());
            formattedDate = currentDate.format(formatter);
        }
        else{
            LocalDate nextDate = currentDate.with(TemporalAdjusters.next(DayOfWeek.valueOf(day.substring(11).toUpperCase())));
            object = raspisanie.getRaspisanie().get(day.substring(11));
            formattedDate = nextDate.format(formatter);
            builder.append("Расписание на " + day.substring(11).toUpperCase() + "\n\n");
            if (nextDate.isAfter(currentDate.plusDays(7 - currentDate.getDayOfWeek().getValue()))) {
                if(weekParity.equals("chet")) weekParity = "nechet";
                else weekParity = "chet";
            }
        }
    }

}
