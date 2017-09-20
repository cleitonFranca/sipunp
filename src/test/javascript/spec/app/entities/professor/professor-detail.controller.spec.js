'use strict';

describe('Controller Tests', function() {

    describe('Professor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfessor, MockTurma, MockCurso;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockTurma = jasmine.createSpy('MockTurma');
            MockCurso = jasmine.createSpy('MockCurso');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Professor': MockProfessor,
                'Turma': MockTurma,
                'Curso': MockCurso
            };
            createController = function() {
                $injector.get('$controller')("ProfessorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unpsipApp:professorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
