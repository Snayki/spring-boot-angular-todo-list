angular.module('todo', ['ui.router']);

angular.module('todo').config(function($stateProvider) {

    $stateProvider.state('todoList', {
        url: '/todo-list',
        templateUrl: 'todo/todo.html'
    });
});

