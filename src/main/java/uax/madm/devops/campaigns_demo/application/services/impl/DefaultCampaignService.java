package uax.madm.devops.campaigns_demo.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import uax.madm.devops.campaigns_demo.application.services.CampaignService;
import uax.madm.devops.campaigns_demo.domain.model.Campaign;
import uax.madm.devops.campaigns_demo.infrastructure.persistence.CampaignRepository;

@Service
public class DefaultCampaignService implements CampaignService {

    private CampaignRepository campaignRepository;

    public DefaultCampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> findById(Long id) {
        return campaignRepository.findById(id);
    }

    @Override
    public Campaign save(Campaign campaign) {
        campaign.setId(null);
        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> findCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Optional<Campaign> updateCampaign(Long id, Campaign campaign) {
        return campaignRepository.findById(id)
                .map(base -> updateFields(base, campaign))
                .map(campaignRepository::save);
    }

    private Campaign updateFields(Campaign base, Campaign updatedCampaign) {
        base.setName(updatedCampaign.getName());
        base.setDescription(updatedCampaign.getDescription());
        return base;
    }
}