'use strict';

describe('Controller Tests', function() {

    describe('ControleAtendimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockControleAtendimento, MockUser, MockCliente;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockControleAtendimento = jasmine.createSpy('MockControleAtendimento');
            MockUser = jasmine.createSpy('MockUser');
            MockCliente = jasmine.createSpy('MockCliente');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ControleAtendimento': MockControleAtendimento,
                'User': MockUser,
                'Cliente': MockCliente
            };
            createController = function() {
                $injector.get('$controller')("ControleAtendimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unpsipApp:controleAtendimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
