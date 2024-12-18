import React, {useEffect, useState} from "react";
import {ENDPOINTS, getEndpoint} from "./type/endpoints";
import {IPoll} from "../types/IPoll";
import axios from "axios";

export function getActivePoll() {
    return axios.get(ENDPOINTS.GET_ACTIVE_POLL.path);
}

export function submitVote(pollId: number, optionId: number) {
    return axios(getEndpoint(ENDPOINTS.SUBMIT_VOTE.path, {pollId: pollId, optionId: optionId}),
        {
            method: ENDPOINTS.SUBMIT_VOTE.method
        });
}