'use strict';

describe('Controller Tests', function() {

    describe('Turma Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTurma, MockProfessor, MockAluno, MockCurso;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTurma = jasmine.createSpy('MockTurma');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockAluno = jasmine.createSpy('MockAluno');
            MockCurso = jasmine.createSpy('MockCurso');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Turma': MockTurma,
                'Professor': MockProfessor,
                'Aluno': MockAluno,
                'Curso': MockCurso
            };
            createController = function() {
                $injector.get('$controller')("TurmaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unpsipApp:turmaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
