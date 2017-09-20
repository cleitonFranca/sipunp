'use strict';

describe('Controller Tests', function() {

    describe('Curso Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCurso, MockProfessor, MockTurma, MockAluno;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCurso = jasmine.createSpy('MockCurso');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockTurma = jasmine.createSpy('MockTurma');
            MockAluno = jasmine.createSpy('MockAluno');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Curso': MockCurso,
                'Professor': MockProfessor,
                'Turma': MockTurma,
                'Aluno': MockAluno
            };
            createController = function() {
                $injector.get('$controller')("CursoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unpsipApp:cursoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
