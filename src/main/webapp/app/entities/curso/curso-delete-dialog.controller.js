(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('CursoDeleteController',CursoDeleteController);

    CursoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Curso'];

    function CursoDeleteController($uibModalInstance, entity, Curso) {
        var vm = this;

        vm.curso = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Curso.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
