'use strict';

describe('Controller Tests', function() {

    describe('DocumentRTS Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDocumentRTS;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDocumentRTS = jasmine.createSpy('MockDocumentRTS');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DocumentRTS': MockDocumentRTS
            };
            createController = function() {
                $injector.get('$controller')("DocumentRTSDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rtsApp:documentRTSUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
