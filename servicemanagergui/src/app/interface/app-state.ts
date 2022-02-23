import { DataState } from "../enum/data-state.enum";

export interface AppState<T> {
    dateState: DataState;
    appData?: T;
    error?: string;
}