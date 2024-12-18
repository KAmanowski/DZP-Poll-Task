import {IOption} from "./IOption";


export interface IPoll {
    id: number;
    question: string;
    options: IOption[];
    totalPollVotes: number;
}