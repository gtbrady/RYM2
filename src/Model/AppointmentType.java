package Model;

public enum AppointmentType {
    OFFICE {
        @Override
        public String toString() {
            return "In Office";
        }
    },
    VIRTUAL {
        @Override
        public String toString() {
            return "Virtual";
        }
    },
    PHONE {
        @Override
        public String toString() {
            return "Phone";
        }
    }
}
