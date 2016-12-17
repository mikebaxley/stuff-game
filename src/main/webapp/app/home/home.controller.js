(function() {
    'use strict';

    angular
        .module('stuffApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', 'GameService', 'GameCreateService', '$state'];

    function HomeController ($scope, Principal, LoginService, GameService, GameCreateService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.games = null;
        vm.createGameDialog = GameCreateService.open;
        
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        loadAll();

        function loadAll() {
        	GameService.findAll(function(result) {
        		console.log('result: ', result);
                vm.games = result;
                
            });
        }
        
        getAccount();

        
        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        function createGame() {
            GameCreateService.open();
        }
    }
})();
