import { NgModule } from '@angular/core';
import { RtsPrivacyRouterModule} from './privacy.router.module';
import {CommonModule} from '@angular/common';
import {RtsPrivacyComponent} from './privacy.component';


@NgModule({
    imports: [
        CommonModule,
        RtsPrivacyRouterModule
    ],
    declarations: [
        RtsPrivacyComponent
    ],
    providers: [

    ]
})

export class RtsPrivacyModule {

}
