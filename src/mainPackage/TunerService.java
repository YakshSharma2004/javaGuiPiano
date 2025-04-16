package mainPackage;

import java.util.List;

public class TunerService {
    private TunerDAO tunerDAO = new TunerDAO();

    public void addTuner(int tunerId, String firstName, String lastName, String phone, String email) {
        Tuner tuner = new Tuner(tunerId, firstName, lastName, phone, email);
        tunerDAO.addTuner(tuner);
    }

    public List<Tuner> getAllTuners() {
        return tunerDAO.getAllTuners();
    }
}
