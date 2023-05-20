import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        currentPathName: '',
        semester: '',
        LongArrangement:{
            longArrangementID: 0,
            labID: 0,
            studentClass: "",
            weekday: "",
            studentCount: 0,
            instructorRequestID: 0,
            endWeek: 0,
            slot: "",
            startWeek: 0
        },
        satisfyingEverythingInstructorRequest:{
            endWeek: 0,
            slot: "",
            labType: "",
            startWeek: 0,
            weekday: "",
            studentCount: 0
        },
        beingArrangedRequest:{
            instructorRequestID: 0,
            requestTime:"",
            course:"",
            labType:"",
            weekday: "",
            studentClass: "",
            studentCount: 0,
            endWeek: 0,
            slot: "",
            startWeek: 0
        }


    },
    mutations: {
        setPath(state) {
            state.currentPathName = localStorage.getItem("currentPathName")
        }
    }
})

export default store