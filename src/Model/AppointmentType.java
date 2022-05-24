package Model;

public enum AppointmentType {
    Office {
        @Override
        public String toString() {
            return "Office";
        }
    },
    Virtual {
        @Override
        public String toString() {
            return "Virtual";
        }
    },
    Phone {
        @Override
        public String toString() {
            return "Phone";
        }
    }
}
