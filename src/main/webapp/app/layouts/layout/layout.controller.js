(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('LayoutController', LayoutController);

    LayoutController.$inject = ['Principal'];

    function LayoutController( Principal) {
        var vm = this;
        vm.isAuthenticated = Principal.isAuthenticated;
    }
})();
