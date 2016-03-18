angular.module('todo').controller('TodoCtrl', ['$scope', '$state', 'ToDoResource',
    function ($scope, $state, ToDoResource) {
        'use strict';

        $scope.init = function () {
            $scope.newTodo = '';

            ToDoResource.query(function (todoList) {
                $scope.todoList = todoList;
                _enableWatch();
            })
        };

        $scope.addTodo = function () {
            var newTodo = {
                title: $scope.newTodo.trim(),
                done: false
            };

            if (!newTodo.title) {
                return;
            }

            ToDoResource.save(newTodo, function (todo) {
                $scope.newTodo = '';
                $scope.todoList.push(todo);
            });
        };

        $scope.removeTodo = function (todo) {
            ToDoResource.remove({id: todo.id}, function () {
                $scope.todoList = _.reject($scope.todoList, todo);
            });
        };

        $scope.updateTodo = function (todo) {
            ToDoResource.update(todo);
        };

        $scope.clearCompletedTodos = function () {
            _.forEach($scope.todoList, function (todo) {
                if (todo.done) {
                    $scope.removeTodo(todo);
                }
            });
        };

        $scope.toggleMarkAll = function () {
            _.forEach($scope.todoList, function (todo) {
                if (todo.done !== $scope.allChecked) {
                    todo.done = $scope.allChecked;
                    $scope.updateTodo(todo);
                }
            });
        };

        function _enableWatch() {
            $scope.$watch('todoList', function () {
                $scope.remainingCount = _.filter($scope.todoList, ['done', false]).length;
                $scope.completedCount = $scope.todoList.length - $scope.remainingCount;
                $scope.allChecked = !$scope.remainingCount;
            }, true);
        }

    }]);

