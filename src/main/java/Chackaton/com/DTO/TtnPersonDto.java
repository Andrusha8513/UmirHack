package Chackaton.com.DTO;

public class TtnPersonDto {
    private String position;
    private String name;

    public TtnPersonDto(String кладовщик, String s) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}