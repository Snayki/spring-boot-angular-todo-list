angular.module('auth').controller('LoginCtrl', function($scope, AuthService){
    $scope.credentials = {};

    $scope.login = function() {
        AuthService.authenticate($scope.credentials);
    };
});
