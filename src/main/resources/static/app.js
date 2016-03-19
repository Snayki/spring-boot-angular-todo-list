angular.module('app', [
    'ui.router',
    'ngResource',
    'http-auth-interceptor',
    'angular-growl',
    'xeditable',
    'utils',
    'auth',
    'todo'
]);

angular.module('app').config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('/todo-list');

    $stateProvider.state('login', {
        url: '/auth/login',
        templateUrl: 'auth/login/login.html',
        controller: 'LoginCtrl'
    });

    $httpProvider.interceptors.push('sessionHandlerInterceptor');
    $httpProvider.interceptors.push('errorHandlerInterceptor');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});

angular.module('app').run(function($rootScope, $http, $state, AuthService, editableOptions) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState, fromStateParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;
        $rootScope.fromState = fromState;
        $rootScope.fromStateParams = fromStateParams;

        if (!AuthService.isAuthenticated()) {
            AuthService.authorize(fromState);
        }
    });

    editableOptions.theme = 'bs3';
});

angular.module('app').controller('AppCtrl', function($scope, AuthService, $state) {

    $scope.isAuthenticated = function() {
        return AuthService.isAuthenticated();
    };

    $scope.logout = function() {
        AuthService.clear();
    };

    $scope.$on('event:auth-loginRequired', function() {
        $state.go('login');
    });

    $scope.$on('event:auth-loginConfirmed', function() {
        $state.go('todoList');
    });
});