import { NgModule } from '@angular/core';
import { RtsPrivacyRoute} from './privacy.route';
import {CommonModule} from '@angular/common';
import {RtsPrivacyComponent} from './privacy.component';


@NgModule({
    imports: [
        CommonModule,
        RtsPrivacyRoute
    ],
    declarations: [
        RtsPrivacyComponent
    ],
    providers: [

    ]
})

export class RtsPrivacyModule {

}
