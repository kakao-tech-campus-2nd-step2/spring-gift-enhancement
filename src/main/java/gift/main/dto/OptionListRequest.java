package gift.main.dto;
import java.util.List;

public class OptionListRequest {
    private List<OptionRequest> optionRequestList;


    public OptionListRequest(List<OptionRequest> optionRequestList) {
        this.optionRequestList = optionRequestList;
    }

    public List<OptionRequest> getOptionRequestList() {
        return optionRequestList;
    }

    @Override
    public String toString() {
        return "OptionListRequest{" +
                "optionRequestList=" + optionRequestList +
                '}';
    }

    public int getSize() {
        return optionRequestList.size();
    }
}
