import './vendor.ts';
import 'hammerjs';


import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';



import {MaterialModule, MdButtonModule, MdSidenavModule} from '@angular/material';
import { Ng2Webstorage } from 'ng2-webstorage';

import { RtsSharedModule, UserRouteAccessService } from './shared';
import { RtsHomeModule } from './home/home.module';
import { RtsAdminModule } from './admin/admin.module';
import { RtsAccountModule } from './account/account.module';
import { RtsEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';


import {
    JhiMainComponent,
    NavbarComponent,
    SidenaveComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        RtsSharedModule,
        RtsHomeModule,
        RtsAdminModule,
        RtsAccountModule,
        RtsEntityModule,
        MaterialModule,MdButtonModule, MdSidenavModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        SidenaveComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        { provide: Window, useValue: window },
        { provide: Document, useValue: document },
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class RtsAppModule {}
