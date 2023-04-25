package streamy.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomModel {

    private final String name;
    private final List<String> values;
    private final int number;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CustomModel)) {
            return false;
        }

        CustomModel c = (CustomModel) o;
        return this.toString().equals(c.toString());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
