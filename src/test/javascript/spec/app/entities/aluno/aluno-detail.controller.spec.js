'use strict';

describe('Controller Tests', function() {

    describe('Aluno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAluno, MockEndereco, MockTurma, MockCurso, MockControleAtendimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAluno = jasmine.createSpy('MockAluno');
            MockEndereco = jasmine.createSpy('MockEndereco');
            MockTurma = jasmine.createSpy('MockTurma');
            MockCurso = jasmine.createSpy('MockCurso');
            MockControleAtendimento = jasmine.createSpy('MockControleAtendimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Aluno': MockAluno,
                'Endereco': MockEndereco,
                'Turma': MockTurma,
                'Curso': MockCurso,
                'ControleAtendimento': MockControleAtendimento
            };
            createController = function() {
                $injector.get('$controller')("AlunoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'unpsipApp:alunoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
