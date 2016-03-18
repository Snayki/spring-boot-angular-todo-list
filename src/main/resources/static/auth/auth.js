angular.module('auth', ['http-auth-interceptor']);

angular.module('auth').factory('AuthService', function($http, $location, $state, authService, growl) {
    var auth = (function() {
        var _authenticated = false;

        return {
            authenticate: function(credentials) {
                postCredentials(credentials)
                    .then(function() {
                        _authenticated = true;
                        authService.loginConfirmed();
                    }).catch(function(err) {
                        growl.error(err.data.message);
                    });
            },
            authorize: function() {
                return $http.get('api/auth/logged', {}, {
                    ignoreAuthModule: true
                }).then(function() {
                    _authenticated = true;
                    return $state.go('todoList');
                }).catch(function() {
                    _authenticated = false;
                    $state.go('login');
                });
            },

            isAuthenticated: function() {
                return _authenticated;
            },

            clear: function() {
                $http.post("api/auth/logout", {}, {
                    ignoreAuthModule: true
                }).success(function() {
                    _authenticated = false;
                    $state.go('login');
                });
            }
        };
    })();

    function postCredentials(credentials) {
        var data = 'j_username=' + encodeURIComponent(credentials.userName) +
            '&j_password=' + encodeURIComponent(credentials.password) +
            '&submit=Login';
        return $http.post('api/authentication', data, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            ignoreAuthModule: true
        });
    }

    return auth;
});
