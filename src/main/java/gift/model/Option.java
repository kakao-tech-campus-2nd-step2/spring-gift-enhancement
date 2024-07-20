package gift.model;

import jakarta.persistence.*;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Option {
    private static final double MAX_OPTION_NUM = 100000000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "optionListList", columnDefinition = "varchar(1000)")
    @Convert(converter = ListStringConverter.class)
    private List<String> optionList = new ArrayList<>();

    protected Option() {
        this.optionList = new ArrayList<>();
    }

    public Option(String optionList){
        List<String> optionLists = List.of(optionList.split(","));
        ifAllcorrectUpdateOption(optionLists);
    }

    public Option(List<String> optionLists){
        ifAllcorrectUpdateOption(optionLists);
    }

    public Option(Long id, List<String> optionLists){
        this.id = id;
        ifAllcorrectUpdateOption(optionLists);
    }

    public List<String> getOptionList() {
        return optionList;
    }

    public long getId() {
        return id;
    }

    private boolean isCorrectOptionSize(List<String> optionLists){
        if(optionLists.isEmpty() || optionLists.size() >= MAX_OPTION_NUM){
            throw new IllegalArgumentException("옵션은 1개 이상 1억개 미만이어야 합니다.");

        }
        return true;
    }

    private boolean isCorrectOptionName(String optionList){
        if(optionList.length()>50){
            throw new IllegalArgumentException("옵션은 최대 50자까지만 입력이 가능합니다.");
        }
        String letters = "()[]+-&/_ ";
        for(int i=0; i<optionList.length(); i++){
            char one = optionList.charAt(i);
            if(!Character.isLetterOrDigit(one) && !letters.contains(Character.toString(one))){
                throw new IllegalArgumentException("옵션 내 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");

            }
        }
        return true;
    }

    private boolean isCorrectOptionList(List<String> optionLists) {
        for (String s : optionLists) {
            if (!isCorrectOptionName(s)) {
                throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
            }
        }
        return true;
    }

    private boolean isNotDuplicate(List<String> optionLists){
        if(optionLists.size() != new HashSet<>(optionLists).size()){
            throw new IllegalArgumentException("옵션은 중복될 수 없습니다.");
        }
        return true;
    }

    private void ifAllcorrectUpdateOption(List<String> optionLists){
        if(isCorrectOptionSize(optionLists) && isCorrectOptionList(optionLists) && isNotDuplicate(optionLists)){
            this.optionList = optionLists;
        }
    }

    public Option delete(String optionListName) {
        List<String> optionLists = this.optionList;
        if(!isCorrectOptionName(optionListName)){
            throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if(optionLists.size() > 1){
            List<String> newOptions = new ArrayList<>();
            newOptions.addAll(optionLists);
            newOptions.remove(optionListName);
            this.optionList = newOptions;
            return this;
        }
        throw new IllegalStateException("옵션은 하나 이상 존재해야 합니다.");
    }

    public Option add(String optionListName){
        List<String> optionLists = this.optionList;
        if(!isCorrectOptionName(optionListName)){
            throw new IllegalArgumentException("옵션은 최대 50자 이내이어야 하며, 특수문자로는 (),[],+,-,&,/,_만 사용 가능합니다.");
        }
        if(optionLists.size() < MAX_OPTION_NUM){
            List<String> newOptions = new ArrayList<>();
            newOptions.addAll(optionLists);
            newOptions.add(optionListName);
            this.optionList = newOptions;
            return this;
        }
        throw new IllegalStateException("옵션은 1억개 이하여야 합니다.");
    }

    public Option update(String oldName, String newName){
        List<String> optionLists = this.optionList;
        List<String> newOptions = new ArrayList<>();
        newOptions.addAll(optionLists);
        if(isCorrectOptionName(newName)){
            newOptions.set(optionList.indexOf(oldName), newName);
            this.optionList = newOptions;
            return this;
        }
        throw new IllegalStateException("잘못된 옵션입니다.");
    }

    public Option remove(int num) {
        List<String> optionLists = this.optionList;
        if(optionLists.size() - num > 0){
            List<String> newOptions = new ArrayList<>();
            newOptions.addAll(optionLists);
            for(int i=0; i<num; i++){
                newOptions.removeFirst();
            }
            this.optionList = newOptions;
            return this;
        }
        throw new IllegalStateException("옵션은 하나 이상 존재해야 합니다.");
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionList=" + optionList +
                '}';
    }
}
