package io.project.SpringBot.Data;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Raspisanie {

    private Map<String, OuterObject> raspisanie = new HashMap<>();

    public Map<String, OuterObject> getRaspisanie() {
        return raspisanie;
    }

    public class InnerObject {
        private String value;
        private String type;

        private InnerObject(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }
    public class OuterObject {
        List<InnerObject> innerObjects;

        public List<InnerObject> getInnerObjects() {
            return innerObjects;
        }

        private OuterObject() {
            this.innerObjects = new ArrayList<>();
        }

        private void addInnerObject(InnerObject obj) {
            innerObjects.add(obj);
        }
    }
    @PostConstruct
    void init(){
        OuterObject monday = new OuterObject();
        OuterObject tuesday = new OuterObject();
        OuterObject wednesday = new OuterObject();
        OuterObject thursday = new OuterObject();
        OuterObject friday = new OuterObject();
        OuterObject saturday = new OuterObject();
        OuterObject sunday = new OuterObject();
        monday.addInnerObject(new InnerObject("08:00\nВоенная подготовка", "any"));

        tuesday.addInnerObject(new InnerObject("08:00-11:10\nУстройства приема и обработки сигналов\n309/5(лаба)\nЦАРЕВА МАРИЯ АНАТОЛЬЕВНА",
                "10.09 08.10 17.09 15.10 12.11 10.12 | 05.11 03.12 01.10 29.10 26.11 24.12"));
        tuesday.addInnerObject(new InnerObject("11:20-12:50\nУстройства приема и обработки сигналов\n301/5(лек)\nЦАРЕВА МАРИЯ АНАТОЛЬЕВНА", "any"));
        tuesday.addInnerObject(new InnerObject("13:30-15:00\nУстройства формирования и генерирования сигналов\n319/5(лек)\nЛОГИНОВ СЕРГЕЙ СЕРГЕЕВИЧ", "any"));

        wednesday.addInnerObject(new InnerObject("08:00-11:10\nУстройства передачи информации широкополосными сигналами\n523/5(лаба)\nАХМЕТШИНА ТАТЬЯНА АНДРЕЕВНА",
                "nechet/chet"));
        wednesday.addInnerObject(new InnerObject("11:20-12:50\nРадиоизмерения\n421/5(лек)\nСУХАРЕВ АЛЕКСАНДР АЛЕКСЕЕВИЧ", "any"));
        wednesday.addInnerObject(new InnerObject("13:30-15:00\nПрикладная электроника\n306/5(лек)\nВАСИЛЬЕВ ИГОРЬ ИВАНОВИЧ", "any"));

        thursday.addInnerObject(new InnerObject("11:20-12:50\nФизическая культура и спорт (элективная)\nКСК КАИ ОЛИМП/КСК КАИ ОЛИМП(прак)\nПРЕПОДАВАТЕЛЬ КАФЕДРЫ",
                "any"));
        thursday.addInnerObject(new InnerObject("13:30-16:40\nУстройства формирования и генерирования сигналов\n308/5(лаба)\nБОБИНА ЕЛЕНА АНДРЕЕВНА",
                "12.09 10.10 07.11 05.12 | 26.09 24.10 21.11 19.12"));
        thursday.addInnerObject(new InnerObject("13:30-15:00\nОсновы проектной деятельности\n513/2(лек)\nВОЛКОВ ВЛАДИМИР РУСЛАНОВИЧ", "chet"));
        thursday.addInnerObject(new InnerObject("15:10-16:40\nОсновы проектной деятельности\n513/2(прак)\nВОЛКОВ ВЛАДИМИР РУСЛАНОВИЧ", "chet"));


        friday.addInnerObject((new InnerObject("08:00-09:30\nТеория колебаний\n523/5(лек)\nДАНИЛАЕВ МАКСИМ ПЕТРОВИЧ", "nechet")));
        friday.addInnerObject((new InnerObject("09:40-11:10\nТеория колебаний\n523/5(прак)\nДАНИЛАЕВ МАКСИМ ПЕТРОВИЧ", "nechet")));
        friday.addInnerObject((new InnerObject("08:00-09:30\nОсновы конструирования и надежности радиоэлектронных средств\n319/5(лек)\nВИНОГРАДОВ ВАСИЛИЙ ЮРЬЕВИЧ", "chet")));
        friday.addInnerObject((new InnerObject("09:40-11:10\nОсновы конструирования и надежности радиоэлектронных средств\n202/5(прак)\nВИНОГРАДОВ ВАСИЛИЙ ЮРЬЕВИЧ", "chet")));
        friday.addInnerObject((new InnerObject("11:20-12:50\nУстройства передачи информации широкополосными сигналами\n527/5(лек)\nЛОГИНОВ СЕРГЕЙ СЕРГЕЕВИЧ", "any")));
        friday.addInnerObject((new InnerObject("13:30-15:00\nУстройства формирования и генерирования сигналов\n312/5(прак)\nБОБИНА ЕЛЕНА АНДРЕЕВНА", "chet")));
        friday.addInnerObject((new InnerObject("13:30-15:00\nПрикладная электроника\n312/5(прак)\nВАСИЛЬЕВ ИГОРЬ ИВАНОВИЧ", "nechet")));
        friday.addInnerObject(new InnerObject("18:25-19:55\nПравоведение\nдистант(лек)\nЛЮБАВИНА ТАТЬЯНА ВИКТОРОВНА", "any"));

        saturday.addInnerObject(new InnerObject("08:00-09:30\nТочность и динамический диапазон радиоэлектронных устройств\n301/5(лек)\nИЛЬИН ГЕРМАН ИВАНОВИЧ", "nechet"));
        saturday.addInnerObject(new InnerObject("09:40-11:10\nУстройства приема и обработки сигналов\n523/5(прак)\nЦАРЕВА МАРИЯ АНАТОЛЬЕВНА", "nechet"));
        saturday.addInnerObject(new InnerObject("11:20-15:00\nРадиоизмерения\n407/5(лаба)\nМАКАРОВ АРСЕНИЙ ДМИТРИЕВИЧ", "28.09 26.10 23.11 21.12 | 14.09 12.10 09.11 07.12"));
        saturday.addInnerObject(new InnerObject("11:20-15:00\nТочность и динамический диапазон радиоэлектронных устройств\n312/5(лаба)\nДАНИЛАЕВ ДМИТРИЙ ПЕТРОВИЧ", "14.09 12.10 09.11 07.12 | 28.09 26.10 23.11 21.12"));

        sunday.addInnerObject(new InnerObject("СЕГОДНЯ ВОСКРЕСЕНЬЕ!", "any"));

        raspisanie.put("monday", monday);
        raspisanie.put("tuesday",tuesday);
        raspisanie.put("wednesday", wednesday);
        raspisanie.put("thursday", thursday);
        raspisanie.put("friday", friday);
        raspisanie.put("saturday", saturday);
        raspisanie.put("sunday", sunday);
    }

}
