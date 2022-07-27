package com.example.demo.payload.response;

import com.example.demo.payload.UserSummary;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class PollRespone {
    private Long id;
    private String question;
    private List<ChoiceResponse> choiceResponses;
    private UserSummary createBy;
    private Instant createDatetime;
    private Instant expirationDateTime;
    private Boolean isExpired;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long selectedChoice;
    private Long totalChoice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceResponse> getChoiceResponses() {
        return choiceResponses;
    }

    public void setChoiceResponses(List<ChoiceResponse> choiceResponses) {
        this.choiceResponses = choiceResponses;
    }

    public UserSummary getCreateBy() {
        return createBy;
    }

    public void setCreateBy(UserSummary createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Instant createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Instant getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(Instant expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Long getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(Long selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public Long getTotalChoice() {
        return totalChoice;
    }

    public void setTotalChoice(Long totalChoice) {
        this.totalChoice = totalChoice;
    }
}
