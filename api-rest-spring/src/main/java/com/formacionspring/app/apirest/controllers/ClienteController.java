package com.formacionspring.app.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionspring.app.apirest.entity.Cliente;
import com.formacionspring.app.apirest.entity.Region;
import com.formacionspring.app.apirest.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {
	
	@Autowired
	private ClienteService servicio;
	
	@GetMapping("/clientes")
	public List<Cliente> cliente(){
		return servicio.findAll();
	}
	
	
	/*@GetMapping("/clientes/{id}")
	public Cliente clienteShow(@PathVariable Long id) {
		return servicio.findById(id);
	}*/
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> clienteShow(@PathVariable Long id){
		Cliente cliente = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			cliente = servicio.findById(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar consulta a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
		
	}
	
	
	
	/*@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente saveCliente(@RequestBody Cliente cliente) {
		return servicio.save(cliente);
	}*/
	
	@PostMapping("/clientes")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			clienteNew = servicio.save(cliente);
			
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar insert a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	
	/*@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente updateCliente(@RequestBody Cliente cliente,@PathVariable Long id) {
		 Cliente clienteUpdate = servicio.findById(id);
		 
		 clienteUpdate.setNombre(cliente.getNombre());
		 clienteUpdate.setApellido(cliente.getApellido());
		 clienteUpdate.setEmail(cliente.getEmail());
		 clienteUpdate.setTelefono(cliente.getTelefono());
		 clienteUpdate.setCreatedAt(cliente.getCreatedAt());
		
		 return servicio.save(clienteUpdate);
		 
	}*/
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente,@PathVariable Long id) {
		
		Cliente clienteActual= servicio.findById(id);
		Map<String,Object> response = new HashMap<>();
		
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setCreatedAt(cliente.getCreatedAt());
			clienteActual.setRegion(cliente.getRegion());
			
			servicio.save(clienteActual);
			
			
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar actualizar a la base de datos");
			response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteActual);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		 
	}
	
	/*
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente deleteCliente(@PathVariable Long id) {
		Cliente clienteBorrado= servicio.findById(id);
		servicio.delete(id);
		return clienteBorrado;
		
	}*/
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
		Cliente clienteBorrado= null;
		Map<String,Object> response = new HashMap<>();
		clienteBorrado=servicio.findById(id);
		
		if(clienteBorrado == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		else {
			
			try {
				
				String nombreFotoAnterior = clienteBorrado.getImagen();
				
				if(nombreFotoAnterior !=null && nombreFotoAnterior.length()>0 ) {
					
					Path rutaFotoAnterior= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
					File archivoFotoanterior = rutaFotoAnterior.toFile();
					
					if(archivoFotoanterior.exists() && archivoFotoanterior.canRead() ) {
						
						archivoFotoanterior.delete();
					}
				}
				
				servicio.delete(id);
				
			} catch (DataAccessException e) {
				response.put("mensaje","Error al eliminar registro de base de datos");
				response.put("error", e.getMessage().concat("_ ").concat(e.getMostSpecificCause().getMessage()));
				
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		response.put("mensaje","El cliente ha sido eliminado con éxito!");
		response.put("cliente", clienteBorrado);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> uploadImagen(@RequestParam("archivo") MultipartFile archivo,@RequestParam("id") Long id){
		
		Map<String,Object> response = new HashMap<>();
		
		Cliente cliente = servicio.findById(id);
		
		if(!archivo.isEmpty()) {
			
			//String nombreArchivo= archivo.getOriginalFilename();
			String nombreArchivo= UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ","");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
		
			try {
				
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
			} catch (IOException e) {
				
				response.put("mensaje","Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat("_ ").concat(e.getCause().getMessage()));
				
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
		
			String nombreFotoAnterior = cliente.getImagen();
			
			if(nombreFotoAnterior !=null && nombreFotoAnterior.length()>0 ) {
				
				Path rutaFotoAnterior= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoanterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoanterior.exists() && archivoFotoanterior.canRead() ) {
					
					archivoFotoanterior.delete();
				}
			}
			
			cliente.setImagen(nombreArchivo);
			
			servicio.save(cliente);
			
			response.put("cliente", cliente);
			response.put("mensaje","subida correcta de imagen "+nombreArchivo);	
			
		}else {
			
			response.put("Archivo","archivo vacio");	
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/clientes/uploads/img/{nombreImagen:.+}")
	public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen){
		
		Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		
		Resource recurso= null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se puede cargar la imagen "+nombreImagen);
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
		
	}
	
	@GetMapping("/clientes/regiones")
	public List<Region> listaRegiones(){
		return servicio.findAllRegiones();
	}
	
	
	

}
