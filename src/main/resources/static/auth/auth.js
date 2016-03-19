angular.module('auth', ['http-auth-interceptor']);

angular.module('auth').factory('AuthService', function($http, $location, $state, authService, growl) {
    var auth = (function() {
        var _authenticated = false;
        var _token = '';

        return {
            authenticate: function(credentials) {
                postCredentials(credentials)
                    .then(function(response) {
                        _authenticated = true;
                        _token  = response.data.token;
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

            getToken: function() {
                return _token;
            },

            setToken: function(token) {
                _token = token
            },

            clear: function() {
                $http.post("api/auth/logout", {}, {
                    ignoreAuthModule: true
                }).success(function() {
                    _authenticated = false;
                    _token = undefined;
                    $state.go('login');
                });
            }
        };
    })();

    function postCredentials(credentials) {
        return $http.post('api/auth/login', '', {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'X-Login': encodeURIComponent(credentials.userName),
                'X-Password': encodeURIComponent(credentials.password)
            },
            ignoreAuthModule: true
        });
    }

    return auth;
});
