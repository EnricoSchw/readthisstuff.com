import {Routes, RouterModule} from "@angular/router";
import { RtsPrivacyComponent} from "./privacy.component";


const PRIVACY_ROUTES: Routes = [{
    path: 'privacy',
    component: RtsPrivacyComponent,
    data: {
        authorities: [],
        pageTitle: 'privacy.title'
    },
}];

export const RtsPrivacyRoute = RouterModule.forChild(PRIVACY_ROUTES);
