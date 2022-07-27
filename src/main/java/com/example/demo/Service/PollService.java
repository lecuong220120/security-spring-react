package com.example.demo.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ChoiceVoteCount;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.payload.PagedRespone;
import com.example.demo.payload.request.PollRequest;
import com.example.demo.payload.response.PollRespone;
import com.example.demo.repository.PollRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VoteRepository;
import com.example.demo.security.UsePrincipal;
import com.example.demo.util.AppConstants;
import com.example.demo.util.ModelMapper;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    PollRepository pollRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PollService.class);
    public PagedRespone<PollRespone> getAllPoll(UsePrincipal usePrincipal, int page, int size){
        validPageNumberAndSize(page,size);
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<Poll> polls = pollRepository.findAll(pageable);
        if (polls.getNumberOfElements() == 0){
            return new PagedRespone<>(Collections.emptyList(),polls.getNumber(),polls.getSize(),polls.getTotalElements(),polls.getTotalPages(),polls.isLast());
        }
        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long,Long> choiceVoteCountMap =  getChoiceVoteCountMap(pollIds);
        Map<Long,Long> pollUserVoteMap = getPollUserVoteMap(usePrincipal,pollIds);
        Map<Long,User> creatorMap = getPollCreateMap(polls.getContent());
        List<PollRespone> pollRespones = polls.map(poll -> {
            return ModelMapper.mapPollToPollRespone(poll,choiceVoteCountMap,creatorMap.get(poll.getCreatedBy()), pollUserVoteMap == null ? null:pollUserVoteMap.getOrDefault(poll.getId(),null));
        }).getContent();
        return new PagedRespone<>(pollRespones, polls.getNumber(),
                polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());    }
    private void validPageNumberAndSize(int page, int size){
        if(page < 0){
            throw new BadRequestException("Page number cannot be less than zero");
        }
        if(size > AppConstants.MAX_PAGE_SIZE){
            throw new BadRequestException("page size must not greater than "+ AppConstants.MAX_PAGE_SIZE);
        }
    }
    private Map<Long,Long> getPollUserVoteMap(UsePrincipal usePrincipal, List<Long> pollIds){
        Map<Long,Long> pollUserVoteMap = null;
        if(usePrincipal !=  null){
            List<Vote> userVotes = voteRepository.findByUserIdAndPollIdIn(usePrincipal.getId(),pollIds);
            pollUserVoteMap = userVotes.stream().collect(Collectors.toMap(vote->vote.getPoll().getId(), vote -> vote.getChoice().getId()));
        }
        return pollUserVoteMap;
    }
    Map<Long, User> getPollCreateMap(List<Poll> polls){
        List<Long> creatorIds = polls.stream().map(Poll::getCreatedBy).distinct().collect(Collectors.toList());
        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long,User> creatorMap = creators.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return creatorMap;
    }
    private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {
        // Retrieve Vote Counts of every Choice belonging to the given pollIds
        List<ChoiceVoteCount> votes = voteRepository.countByPollIdInGroupByChoiceId(pollIds);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(ChoiceVoteCount::getChoiceId, ChoiceVoteCount::getVoteCount));

        return choiceVotesMap;
    }
}
