import * as layout from './layout.actions';
export interface State { /* The description of the different parts of the layout go here */
}


const initialState: State = {/* The initial values of the layout state will be initialized here */};


/* The reducer of the layout state. Each time an action for the layout is dispatched, it will create a new state for the layout. */


export function reducer(state = initialState, action: layout.LayoutActions): State {
    switch (action.type) {
        default:
            return state;
    }
}
