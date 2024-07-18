package gift.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "Option", columnDefinition = "varchar(1000)")
    @Convert(converter = ListStringConverter.class)
    private List<String> option = new ArrayList<>();

    protected Option() {
        this.option = new ArrayList<>();
    }

    public Option(String option){
        System.out.println("옵션받아옴" + option);
        List<String> options = List.of(option.split(","));
        if(isCorrectOptionSize(options) && isCorrectOptionList(options) && isNotDuplicate(options)){
            this.option = options;
        }
    }

    public Option(List<String> options){
        if(isCorrectOptionSize(options) && isCorrectOptionList(options) && isNotDuplicate(options)){
            this.option = options;
        }
    }

    public Option(Long id, List<String> options){
        this.id = id;
        if(isCorrectOptionSize(options) && isCorrectOptionList(options) && isNotDuplicate(options)){
            this.option = options;
        }
    }

    public List<String> getOptionList() {
        return option;
    }

    public long getId() {
        return id;
    }

    private boolean isCorrectOptionSize(List<String> options){
        System.out.println("11" + options);
        if(options.isEmpty() || options.size() >= 100000000){
            throw new IllegalArgumentException("옵션은 1개 이상 1억개 미만이어야 합니다.");

        }
        return true;
    }

    private boolean isCorrectOptionName(String option){
        System.out.println("22" + option);
        if(option.length()>50){
            throw new IllegalArgumentException("옵션은 최대 50자까지만 입력이 가능합니다.");
        }
        String letters = "()[]+-&/_ ";
        for(int i=0; i<option.length(); i++){
            char one = option.charAt(i);
            if(!Character.isLetterOrDigit(one) && !letters.contains(Character.toString(one))){
                throw new IllegalArgumentException("옵션 내 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");

            }
        }
        return true;
    }

    private boolean isCorrectOptionList(List<String> options) {
        System.out.println("33" + options);
        for (String s : options) {
            if (!isCorrectOptionName(s)) {
                throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
            }
        }
        return true;
    }

    private boolean isNotDuplicate(List<String> options){
        System.out.println("44" + options);
        if(options.size() != new HashSet<>(options).size()){
            throw new IllegalArgumentException("옵션은 중복될 수 없습니다.");
        }
        return true;
    }

    public Option delete(String optionName) {
        List<String> options = this.option;
        if(!isCorrectOptionName(optionName)){
            throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if(options.size() > 1){
            List<String> newOptions = new ArrayList<>();
            newOptions.addAll(options);
            newOptions.remove(optionName);
            this.option = newOptions;
            return this;
        }
        throw new IllegalStateException("옵션은 하나 이상 존재해야 합니다.");
    }

    public Option add(String optionName){
        List<String> options = this.option;
        if(!isCorrectOptionName(optionName)){
            throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if(options.size() < 100000000){
            List<String> newOptions = new ArrayList<>();
            newOptions.addAll(options);
            newOptions.add(optionName);
            this.option = newOptions;
            return this;
        }
        throw new IllegalStateException("옵션은 1억개 이하여야 합니다.");
    }

    public Option update(String oldName, String newName){
        List<String> options = this.option;
        List<String> newOptions = new ArrayList<>();
        newOptions.addAll(options);
        if(isCorrectOptionName(newName)){
            newOptions.set(option.indexOf(oldName), newName);
            this.option = newOptions;
            return this;
        }
        throw new IllegalStateException("잘못된 옵션입니다.");
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", option=" + option +
                '}';
    }
}
