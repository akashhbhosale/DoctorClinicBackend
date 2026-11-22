package com.doctorclinicapp.backend.service.medication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doctorclinicapp.backend.dto.medication.AddPrescriptionTemplateMedicineRequest;
import com.doctorclinicapp.backend.dto.medication.CreatePrescriptionTemplateRequest;
import com.doctorclinicapp.backend.dto.medication.PrescriptionTemplateMedicineResponse;
import com.doctorclinicapp.backend.dto.medication.PrescriptionTemplateResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.model.medication.DirectionOfUseMaster;
import com.doctorclinicapp.backend.model.medication.DoseFormMaster;
import com.doctorclinicapp.backend.model.medication.MedicationPrescriptionTemplateMedicine;
import com.doctorclinicapp.backend.model.medication.MedicineMaster;
import com.doctorclinicapp.backend.model.medication.PrescriptionTemplate;
import com.doctorclinicapp.backend.model.medication.RouteOfAdministrationMaster;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.repository.medication.DirectionOfUseMasterRepository;
import com.doctorclinicapp.backend.repository.medication.DoseFormMasterRepository;
import com.doctorclinicapp.backend.repository.medication.MedicationPrescriptionTemplateMedicineRepository;
import com.doctorclinicapp.backend.repository.medication.MedicationPrescriptionTemplateRepository;
import com.doctorclinicapp.backend.repository.medication.MedicineMasterRepository;
import com.doctorclinicapp.backend.repository.medication.RouteOfAdministrationMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicationPrescriptionTemplateService {

	private final MedicationPrescriptionTemplateRepository templateRepository;
	private final MedicationPrescriptionTemplateMedicineRepository medicineTemplateRepository;

	private final DoctorRepository doctorRepository;
	private final MedicineMasterRepository medicineRepository;
	private final DoseFormMasterRepository doseFormRepository;
	private final RouteOfAdministrationMasterRepository routeRepository;
	private final DirectionOfUseMasterRepository directionRepository;

	@Transactional
	public PrescriptionTemplateResponse createTemplate(CreatePrescriptionTemplateRequest request, String username) {

		Doctor doctor = doctorRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", username));

		PrescriptionTemplate template = new PrescriptionTemplate();
		template.setTemplateName(request.getTemplateName());
		template.setDescription(request.getDescription());
		template.setDoctor(doctor);

		PrescriptionTemplate savedTemplate = templateRepository.save(template);

		List<MedicationPrescriptionTemplateMedicine> templateMedicines = new ArrayList<>();

		for (AddPrescriptionTemplateMedicineRequest req : request.getMedicines()) {

			MedicineMaster medicine = medicineRepository.findById(req.getMedicineMasterId())
					.orElseThrow(() -> new ResourceNotFoundException("Medicine", "id", req.getMedicineMasterId()));

			DirectionOfUseMaster direction = directionRepository.findById(req.getDirectionOfUseId()).orElseThrow(
					() -> new ResourceNotFoundException("DirectionOfUse", "id", req.getDirectionOfUseId()));

			MedicationPrescriptionTemplateMedicine item = new MedicationPrescriptionTemplateMedicine();

			item.setPrescriptionTemplate(savedTemplate);
			item.setMedicineMaster(medicine);
			item.setDirectionOfUse(direction);
			item.setStrength(req.getStrength());
			item.setDosage(req.getDosage());
			item.setDurationDays(req.getDurationDays());
			item.setQuantity(req.getQuantity());
			item.setQuantityUnit(req.getQuantityUnit());
			item.setComment(req.getComment());

			if (req.getDoseFormId() != null) {
				DoseFormMaster doseForm = doseFormRepository.findById(req.getDoseFormId())
						.orElseThrow(() -> new ResourceNotFoundException("DoseForm", "id", req.getDoseFormId()));
				item.setDoseForm(doseForm);
			}

			if (req.getRouteOfAdministrationId() != null) {
				RouteOfAdministrationMaster route = routeRepository.findById(req.getRouteOfAdministrationId())
						.orElseThrow(
								() -> new ResourceNotFoundException("Route", "id", req.getRouteOfAdministrationId()));
				item.setRouteOfAdministration(route);
			}

			templateMedicines.add(item);
		}

		List<MedicationPrescriptionTemplateMedicine> savedMedicines = medicineTemplateRepository
				.saveAll(templateMedicines);

		return mapToResponse(savedTemplate, savedMedicines);
	}

	// Get Method From DTO
	private PrescriptionTemplateResponse mapToResponse(PrescriptionTemplate template,
			List<MedicationPrescriptionTemplateMedicine> medicines) {
		List<PrescriptionTemplateMedicineResponse> medicineResponses = medicines
				.stream().map(
						item -> PrescriptionTemplateMedicineResponse.builder().id(item.getId())
								.medicineMasterId(item.getMedicineMaster().getId())
								.medicineName(item.getMedicineMaster().getMedicineName())
								.medicineType(item.getMedicineMaster().getMedicineType().name())
								.doseFormId(item.getDoseForm() != null ? item.getDoseForm().getId() : null)
								.doseForm(item.getDoseForm() != null ? item.getDoseForm().getDoseFormName() : null)
								.routeOfAdministrationId(item.getRouteOfAdministration() != null
										? item.getRouteOfAdministration().getId()
										: null)
								.routeOfAdministration(item.getRouteOfAdministration() != null
										? item.getRouteOfAdministration().getRouteName()
										: null)
								.directionOfUseId(item.getDirectionOfUse().getId())
								.directionOfUse(item.getDirectionOfUse().getDirectionName())
								.strength(item.getStrength()).dosage(item.getDosage())
								.durationDays(item.getDurationDays()).quantity(item.getQuantity())
								.quantityUnit(item.getQuantityUnit()).comment(item.getComment()).build())
				.collect(Collectors.toList());

		return PrescriptionTemplateResponse.builder().id(template.getId()).templateName(template.getTemplateName())
				.description(template.getDescription()).doctorId(template.getDoctor().getId())
				.doctorName(template.getDoctor().getFullName()).createdAt(template.getCreatedAt())
				.updatedAt(template.getUpdatedAt()).medicines(medicineResponses).build();
	}

	// Get Method
	@Transactional(readOnly = true)
	public List<PrescriptionTemplateResponse> getMyTemplates(String username) {

		Doctor doctor = doctorRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", username));

		List<PrescriptionTemplate> templates = templateRepository.findByDoctor_Id(doctor.getId());

		return templates.stream().map(template -> {
			List<MedicationPrescriptionTemplateMedicine> medicines = medicineTemplateRepository
					.findByPrescriptionTemplate_Id(template.getId());

			return mapToResponse(template, medicines);
		}).collect(Collectors.toList());
	}

	// Update Method
	@Transactional
	public PrescriptionTemplateResponse updateTemplate(Long templateId, CreatePrescriptionTemplateRequest request,
			String username) {

		Doctor doctor = doctorRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", username));

		PrescriptionTemplate template = templateRepository.findById(templateId)
				.orElseThrow(() -> new ResourceNotFoundException("PrescriptionTemplate", "id", templateId));

		if (!template.getDoctor().getId().equals(doctor.getId())) {
			throw new RuntimeException("You are not authorized to update this template");
		}

		template.setTemplateName(request.getTemplateName());
		template.setDescription(request.getDescription());

		PrescriptionTemplate updatedTemplate = templateRepository.save(template);

		// Remove existing medicines
		medicineTemplateRepository.deleteByPrescriptionTemplate_Id(templateId);

		// Add updated medicines
		List<MedicationPrescriptionTemplateMedicine> updatedMedicines = new ArrayList<>();

		for (AddPrescriptionTemplateMedicineRequest req : request.getMedicines()) {

			MedicineMaster medicine = medicineRepository.findById(req.getMedicineMasterId())
					.orElseThrow(() -> new ResourceNotFoundException("Medicine", "id", req.getMedicineMasterId()));

			DirectionOfUseMaster direction = directionRepository.findById(req.getDirectionOfUseId()).orElseThrow(
					() -> new ResourceNotFoundException("DirectionOfUse", "id", req.getDirectionOfUseId()));

			MedicationPrescriptionTemplateMedicine item = new MedicationPrescriptionTemplateMedicine();

			item.setPrescriptionTemplate(updatedTemplate);
			item.setMedicineMaster(medicine);
			item.setDirectionOfUse(direction);
			item.setStrength(req.getStrength());
			item.setDosage(req.getDosage());
			item.setDurationDays(req.getDurationDays());
			item.setQuantity(req.getQuantity());
			item.setQuantityUnit(req.getQuantityUnit());
			item.setComment(req.getComment());

			if (req.getDoseFormId() != null) {
				DoseFormMaster doseForm = doseFormRepository.findById(req.getDoseFormId())
						.orElseThrow(() -> new ResourceNotFoundException("DoseForm", "id", req.getDoseFormId()));
				item.setDoseForm(doseForm);
			}

			if (req.getRouteOfAdministrationId() != null) {
				RouteOfAdministrationMaster route = routeRepository.findById(req.getRouteOfAdministrationId())
						.orElseThrow(
								() -> new ResourceNotFoundException("Route", "id", req.getRouteOfAdministrationId()));
				item.setRouteOfAdministration(route);
			}

			updatedMedicines.add(item);
		}

		List<MedicationPrescriptionTemplateMedicine> savedMedicines = medicineTemplateRepository
				.saveAll(updatedMedicines);

		return mapToResponse(updatedTemplate, savedMedicines);
	}

	// Get By ID
	@Transactional(readOnly = true)
	public PrescriptionTemplateResponse getTemplateById(Long templateId, String username) {

		Doctor doctor = doctorRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", username));

		PrescriptionTemplate template = templateRepository.findById(templateId)
				.orElseThrow(() -> new ResourceNotFoundException("PrescriptionTemplate", "id", templateId));

		if (!template.getDoctor().getId().equals(doctor.getId())) {
			throw new RuntimeException("You are not authorized to access this template");
		}

		List<MedicationPrescriptionTemplateMedicine> medicines = medicineTemplateRepository
				.findByPrescriptionTemplate_Id(templateId);

		return mapToResponse(template, medicines);
	}

	// Delete Method
	@Transactional
	public void deleteTemplate(Long templateId, String username) {

		Doctor doctor = doctorRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", username));

		PrescriptionTemplate template = templateRepository.findById(templateId)
				.orElseThrow(() -> new ResourceNotFoundException("PrescriptionTemplate", "id", templateId));

		if (!template.getDoctor().getId().equals(doctor.getId())) {
			throw new RuntimeException("You are not authorized to delete this template");
		}

		medicineTemplateRepository.deleteByPrescriptionTemplate_Id(templateId);

		templateRepository.delete(template);
	}
}
