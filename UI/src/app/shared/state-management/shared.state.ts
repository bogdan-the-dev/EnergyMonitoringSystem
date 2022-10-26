import {SharedReducer, SharedState} from "./shared.reducer";
import {ActionReducerMap} from "@ngrx/store";

export interface SharedModuleState {
  sharedState: SharedState
}

export const sharedReducerMap: ActionReducerMap<SharedModuleState> = {
  sharedState: SharedReducer
}

export const stateFeatureName = 'sharedModuleFeature'
