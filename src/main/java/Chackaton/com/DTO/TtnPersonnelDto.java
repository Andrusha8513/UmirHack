package Chackaton.com.DTO;

public class TtnPersonnelDto {
    private TtnPersonDto approvedBy;
    private TtnPersonDto storekeeper;
    private TtnPersonDto shippedBy;
    private TtnPersonDto receivedBy;

    public TtnPersonnelDto(TtnPersonDto начальникСклада, TtnPersonDto кладовщик, TtnPersonDto кладовщик1, TtnPersonDto менеджерПоЗакупкам) {
    }

    public TtnPersonDto getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(TtnPersonDto approvedBy) {
        this.approvedBy = approvedBy;
    }

    public TtnPersonDto getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(TtnPersonDto receivedBy) {
        this.receivedBy = receivedBy;
    }

    public TtnPersonDto getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(TtnPersonDto shippedBy) {
        this.shippedBy = shippedBy;
    }

    public TtnPersonDto getStorekeeper() {
        return storekeeper;
    }

    public void setStorekeeper(TtnPersonDto storekeeper) {
        this.storekeeper = storekeeper;
    }
}
