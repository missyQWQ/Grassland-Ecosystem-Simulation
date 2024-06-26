//YichunZhang k19012458 & YuanfeiQiu k19030615 
/**
 * Class Disease - Animals may get disease and might die of disease.
 * There are 2 diseases: Parasitic Disease and Brucellosis.
 * Parasitic Disease: 
 *  Infection occurs when two animals meet. 
 *  Spread through animal-animal contact.
 * Brucellosis: 
 *  Affect only particular species (Sheep, Tiger, Wolf).
 *  At first it only spreads in sheep through sheep-sheep contact.
 *  Then tiger and wolf will contract it from sheep if they consume sheep.
 *  Finally it spreads through tiger-tiger contact, wolf-wolf contact, sheep-sheep contact.
 *
 * @author Yichun Zhang & Yuanfei Qiu
 * @version 2020/02/22
 */
public class Disease
{
    // A string array contains all the disease.
    private static String[] disease_container = {"Parasitic Disease", 
                                                "Brucellosis"};
    // Latent period of the disease. 
    // During this period, animal can infect other animals. 
    // At the end of this period, animal will either die or cure. 
    // -1 -> healthy.
    private int latentPeriod;
    // An int variable represents for disease status.
    // -1 -> healthy, 0 -> Parasitic Disease, 1 -> Brucellosis.
    private int curStatus;

    /**
     * Create a disease with the type of disease and latent period.
     * @param curStatus Current disease status, 0 means parasitic disease, 
     * 1 means brucellosis, -1 indicates healthy.
     * @param latentPeriod Latent period, -1 if healthy.
     */
    public Disease(int curStatus, int latentPeriod)
    {
        this.curStatus = curStatus;
        this.latentPeriod = latentPeriod;
    }

    /**
     * Return current disease status.
     * 0 indicates parasitic disease, 1 indicates brucellosis, -1 indicates healthy.
     */
    public int getCurStatus()
    {
        return curStatus;
    }

    /**
     * Return latent period, -1 if healthy.
     * During this period, animal can infect other animals. 
     * At the end of this period, animal will either die or cure.
     */
    public int getLatentPeriod()
    {
        return latentPeriod;
    }

    /**
     * Set latent period, -1 if healthy.
     * @param latentPeriod
     */
    public void setLatentPeriod(int latentPeriod)
    {
        this.latentPeriod = latentPeriod;
    }

    /**
     * Set disease with the type of disease and latent period.
     * @param curStatus Current disease status, 0 means parasitic disease, 
     * 1 means brucellosis, -1 indicates healthy.
     * @param latentPeriod Latent period, -1 if healthy.
     */
    public void setDisease(int curStatus, int latentPeriod)
    {
        this.curStatus = curStatus;
        this.latentPeriod = latentPeriod;
    }

    /**
     * Reset the disease. This means set the status as "healthy" again.
     */
    public void reset()
    {
        curStatus = -1;
        latentPeriod = -1;
    }
    
    /**
     * Return the name of disease.
     * @param diseaseType 0 indicates parasitic disease, 1 indicates brucellosis.
     */
    public static String getDiseaseName(int diseaseType)
    {
        return disease_container[diseaseType];
    }
}