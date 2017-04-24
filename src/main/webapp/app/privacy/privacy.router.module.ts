import {Routes, RouterModule} from "@angular/router";
import { RtsPrivacyComponent} from "./privacy.component";


const PRIVACY_ROUTES: Routes = [{
    path: 'privacy',
    component: RtsPrivacyComponent
}];

export const RtsPrivacyRouterModule = RouterModule.forChild(PRIVACY_ROUTES);
