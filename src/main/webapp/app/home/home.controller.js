(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'DocumentRTS'];

    function HomeController($scope, Principal, LoginService, $state, DocumentRTS) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        loadAll();


        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }


        function loadAll() {
            DocumentRTS.query(function (result) {
                var subStuffSet = [],
                    stuffSet = [],
                    i = 0;

                // Create 3 col set
                // vm.stuffSet = [
                //     [{id: 1}, {id: 2}, {id: 3}],
                //     [{id: 4}, {id: 5}, {id: 6}],
                //     [{id: 7}, {id: 8}, {id: 9}]
                // ];
                angular.forEach(result, function (value, key) {

                    value = fillUp(value);

                    subStuffSet.push(value);


                    if (i % 3 == 2) {
                        this.push(subStuffSet);
                        subStuffSet = []
                    }
                    i++;
                }, stuffSet);

                // Add the last row if not finished
                if (i % 3 != 0) {
                    stuffSet.push(stuffSet.push(subStuffSet));
                }

                vm.stuffSet = stuffSet;
            });
        }

        function fillUp(doc) {
            if (isBlank(doc.title))
                doc.title = "Unknown";
            return doc;
        }

        function isBlank(string) {
            return (!string || !string.length);
        }
    }
})();
