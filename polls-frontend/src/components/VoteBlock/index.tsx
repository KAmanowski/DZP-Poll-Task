import React, {useEffect, useState} from 'react';
import './style/style.css';
import {IOption} from "../../types/IOption";

export interface IVoteBlockProps {
    showResult: boolean;
    option: IOption;
    totalPollVotes: number;
    onVoteClick: (pollId: number, optionId: number) => void;
    selected: boolean;
}

function VoteBlock(props: IVoteBlockProps) {

    const resultSlice = Math.round((props.option.currentVoteCount / props.totalPollVotes) * 100);

    function onClick() {
        if (!props.showResult) {
            props.onVoteClick(props.option.pollId, props.option.id);
        }
    }

    return (
        <div
             className={"vote-block" + (props.selected ? " selected" : "") + (props.showResult ? " result" : "")}
             onClick={onClick}>
            <div className="content">{props.option.content}</div>
            {props.showResult && <div className="result-text">{resultSlice}% ({props.option.currentVoteCount})</div>}
        </div>
    );
}

export default VoteBlock;
