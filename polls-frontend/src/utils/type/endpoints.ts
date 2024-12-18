const BASE_URL = 'http://localhost:8080/api';

export const ENDPOINTS = {
    GET_ALL_POLLS: {
        path: BASE_URL + "/poll",
        method: "GET",
    },
    SUBMIT_VOTE: {
        path: BASE_URL + "/poll/:pollId/option/:optionId/vote",
        method: "POST",
    },
    GET_ACTIVE_POLL: {
        path: BASE_URL + "/poll/active",
        method: "GET",
    },
}

export const getEndpoint = (endpoint: string, arg: object) => {
    let newEndpoint = endpoint;
    Object.keys(arg).forEach((key) => {
        const regex = new RegExp('(:' + key + ')', 'g');
        newEndpoint = newEndpoint.replace(regex, arg[key]);
    })
    return newEndpoint;
}

