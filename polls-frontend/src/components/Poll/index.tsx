import React, {useEffect, useState} from 'react';
import './style/style.css';
import {IPoll} from "../../types/IPoll";
import axios from "axios";
import {ENDPOINTS} from "../../utils/type/endpoints";
import {getActivePoll, submitVote} from "../../utils/apiCalls";
import VoteBlock from "../VoteBlock";

function Poll() {

    const [activePoll, setActivePoll] = useState<IPoll>({id: 0, options: [], question: "", totalPollVotes: 0});
    const [showResult, setShowResult] = useState<boolean>(false);
    const [showError, setShowError] = useState<boolean>(false);
    const [selectedOptionId, setSelectedOptionId] = useState<number>(0);

    // Would be good to add some loading icons triggered by boolean state as an improvement if there was more time
    useEffect(() => {
        refreshActivePoll();
    }, [])

    function refreshActivePoll() {
        getActivePoll().then(response => {
            setActivePoll(response.data)
            setShowError(false)
        }).catch(error => {
            setShowError(true)
        });
    }

    function onVoteClick(pollId: number, optionId: number) {
        submitVote(pollId, optionId).then(response => {
            setSelectedOptionId(optionId);
            setShowResult(true);
            refreshActivePoll();
        });
    }

    if (showError) {
        return (<div>An error has occurred.</div>);
    }

    return (
        <div>
            <div className="question">{showError ? 'An error has occurred.' : activePoll.question}</div>
            {showError ? '' : <div className="vote-blocks">
                {activePoll.options.map(option =>
                    <VoteBlock showResult={showResult} option={option} selected={selectedOptionId === option.id}
                               totalPollVotes={activePoll.totalPollVotes} onVoteClick={onVoteClick}/>)}
            </div>}
            {(showError || !showResult) ? '' : <div className="total-count">Total Votes: {activePoll.totalPollVotes}</div>}
        </div>
    );
}

export default Poll;
