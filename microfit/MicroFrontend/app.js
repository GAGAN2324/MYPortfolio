const token =
    localStorage.getItem("token");

if (!token) {

    window.location.href =
        "login.html";
}
// ===============================
// PRO-FIT Dashboard
// ===============================

window.onload = function () {

    loadDashboard();

    loadWeightChart();

    createWorkoutChart();
};

// =================================
// LOAD DATA FROM SPRING BOOT
// =================================

function loadDashboard() {

    fetch("http://localhost:8080/api/workouts")

        .then(response => response.json())

        .then(data => {

            let workoutTable =
                document.getElementById("workoutTable");

            workoutTable.innerHTML = "";

            let totalCalories = 0;

            let totalDuration = 0;

            data.forEach(workout => {

                totalCalories += workout.caloriesBurned;

                totalDuration += workout.durationMinutes;

                workoutTable.innerHTML += `

                <tr>

                    <td>${workout.exerciseName}</td>

                    <td>${workout.durationMinutes} min</td>

                    <td>${workout.caloriesBurned}</td>

                    <td>${workout.workoutDate}</td>

                    <td>
                        <span class="completed">
                            Completed
                        </span>
                    </td>

                </tr>

                `;
            });

            document.getElementById("workouts")
                .innerText = data.length;

            document.getElementById("calories")
                .innerText = totalCalories;

            let avgDuration = 0;

            if (data.length > 0) {

                avgDuration =
                    Math.round(totalDuration / data.length);
            }

            document.getElementById("active")
                .innerText = avgDuration;

        })

        .catch(error => {

            console.log(error);

            alert("Cannot connect to backend");
        });
}

// =================================
// REAL WEIGHT CHART FROM DATABASE
// =================================

function loadWeightChart() {

    fetch("http://localhost:8080/api/weights")

        .then(response => response.json())

        .then(data => {

            const labels =
                data.map(item => item.entryDate);

            const weights =
                data.map(item => item.weight);

            const weightCanvas =
                document.getElementById("weightChart");

            if (!weightCanvas) return;

            new Chart(weightCanvas, {

                type: "line",

                data: {

                    labels: labels,

                    datasets: [{

                        label: "Weight (KG)",

                        data: weights,

                        borderColor: "#f59e0b",

                        backgroundColor:
                            "rgba(245,158,11,0.2)",

                        fill: true,

                        tension: 0.4,

                        borderWidth: 3
                    }]
                },

                options: {

                    responsive: true,

                    plugins: {

                        legend: {

                            labels: {
                                color: "white"
                            }
                        }
                    },

                    scales: {

                        x: {

                            ticks: {
                                color: "white"
                            }
                        },

                        y: {

                            ticks: {
                                color: "white"
                            }
                        }
                    }
                }
            });

        })

        .catch(error => {

            console.log(
                "Weight Chart Error:",
                error
            );
        });
}

// =================================
// WORKOUT CHART
// =================================

function createWorkoutChart() {

    const workoutCanvas =
        document.getElementById("workoutChart");

    if (!workoutCanvas) return;

    new Chart(workoutCanvas, {

        type: "bar",

        data: {

            labels: [
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat",
                "Sun"
            ],

            datasets: [{

                label: "Calories Burned",

                data: [
                    300,
                    450,
                    600,
                    500,
                    700,
                    850,
                    550
                ],

                backgroundColor: "#f59e0b",

                borderRadius: 10
            }]
        },

        options: {

            responsive: true,

            plugins: {

                legend: {

                    labels: {
                        color: "white"
                    }
                }
            },

            scales: {

                x: {

                    ticks: {
                        color: "white"
                    }
                },

                y: {

                    ticks: {
                        color: "white"
                    }
                }
            }
        }
    });
}

// =================================
// SAVE WORKOUT
// =================================

function saveWorkout() {

    const workout = {

        exerciseName:
            document.getElementById("exerciseName").value,

        durationMinutes:
            parseInt(
                document.getElementById("durationMinutes").value
            ),

        caloriesBurned:
            parseInt(
                document.getElementById("caloriesBurned").value
            ),

        workoutDate:
            document.getElementById("workoutDate").value
    };

    fetch(
        "http://localhost:8080/api/workouts",
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(workout)
        }
    )

    .then(response => response.json())

    .then(data => {

        alert("Workout Saved Successfully!");

        location.reload();
    })

    .catch(error => {

        console.log(error);

        alert("Error Saving Workout");
    });
}
// =================================
// SAVE WEIGHT
// =================================

function saveWeight() {

    const weightEntry = {

        weight:
            parseFloat(
                document.getElementById("weight").value
            ),

        entryDate:
            document.getElementById("entryDate").value
    };

    fetch(
        "http://localhost:8080/api/weights",
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body:
                JSON.stringify(weightEntry)
        }
    )

    .then(response => response.json())

    .then(data => {

        alert("Weight Saved Successfully!");

        location.reload();
    })

    .catch(error => {

        console.log(error);

        alert("Error Saving Weight");
    });
}