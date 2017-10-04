(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ProfessorController', ProfessorController);

    ProfessorController.$inject = ['Professor', 'ProfessorSearch'];

    function ProfessorController(Professor, ProfessorSearch) {

        var vm = this;

        vm.professors = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Professor.query(function(result) {
                vm.professors = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProfessorSearch.query({query: vm.searchQuery}, function(result) {
                vm.professors = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
