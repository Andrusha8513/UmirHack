package Chackaton.com.DTO;

import java.time.LocalDateTime;

public class TtnSystemInfoDto {
    private LocalDateTime generationDate;
    private String operationId;
    private String systemVersion;

    public TtnSystemInfoDto(LocalDateTime now, String s, String s1) {
    }

    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}