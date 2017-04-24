import { Component } from '@angular/core';
import {JhiLanguageService} from "ng-jhipster";

@Component({
    templateUrl: './privacy.component.html',
    styleUrls: [
        'privacy.scss'
    ]
})
export class RtsPrivacyComponent {

    private title;

    constructor(
        private jhiLanguageService: JhiLanguageService
    ) {
        this.jhiLanguageService.setLocations(['privacy']);
        this.title = 'privacy';
    }
}
