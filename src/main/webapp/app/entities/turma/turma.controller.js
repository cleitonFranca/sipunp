(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('TurmaController', TurmaController);

    TurmaController.$inject = ['Turma', 'TurmaSearch'];

    function TurmaController(Turma, TurmaSearch) {

        var vm = this;

        vm.turmas = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Turma.query(function(result) {
                vm.turmas = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TurmaSearch.query({query: vm.searchQuery}, function(result) {
                vm.turmas = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
