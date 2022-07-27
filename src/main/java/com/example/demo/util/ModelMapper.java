package com.example.demo.util;

import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.payload.response.ChoiceResponse;
import com.example.demo.payload.response.PollRespone;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ModelMapper {
    public static PollRespone mapPollToPollRespone(Poll poll, Map<Long, Long> choiceVoteMap, User creator, Long userVote){
        PollRespone pollRespone = new PollRespone();
        pollRespone.setId(poll.getId());
        pollRespone.setQuestion(poll.getQuestion());
        pollRespone.setCreateDatetime(poll.getCreateAt());
        pollRespone.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollRespone.setExpired(poll.getExpirationDateTime().isBefore(now));
        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice ->{
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());
            if()
        })
    }
}
