import {Action} from '@ngrx/store';

export const LayoutActionTypes = {OPEN_MODAL: '[Layout] Open modal', CLOSE_MODAL: '[Layout] Close modal'};
/* Modal actions */
export class OpenModalAction implements Action {
    type = LayoutActionTypes.OPEN_MODAL;

    constructor(public payload: string) {
    }
}
export class CloseModalAction implements Action {
    type = LayoutActionTypes.CLOSE_MODAL;

    constructor() {
    }
}

export type LayoutActions = CloseModalAction | OpenModalAction
