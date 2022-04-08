package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.main.Driver;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class ReimbursementController implements Controller{

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.jwtService = JWTService.getInstance();
        this.reimbursementService = new ReimbursementService();
    }

    private final Handler getAllUnfulfilledReimbursements = ctx ->  {

        logger.info("\nGet All Reimbursements Endpoint has been invoked." +
                "\nPath: " + ctx.path() +
                "\nHeader Names : " + ctx.res.getHeaderNames() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        // create a web token "Authorization" in header of the request
        // this will contain the token to be parsed
        String jwt = Objects.requireNonNull(ctx.header("Authorization")).split(" ")[1];

        // check token provided by end user
        Jws<Claims> token = this.jwtService.parseJWT(jwt);

        // validate tokens authority and if it's a Manager
        if (!token.getBody().get("user_role").equals("Manager")) {
            logger.info("\nAUTHORIZATION ATTACK HAS BEEN INITIATED! " +
                    "\nA user is attempting to access an endpoint greater than their set permissions! " +
                    "\nCurrent req method = " + ctx.req.getMethod() + "\n");
            // Unauthorized response = 401 status code
            throw new UnauthorizedResponse("You must be a trainer to access this point");
        }

        List<ResponseReimbursementDTO> reimbursements = this.reimbursementService.getAllPendingReimbursements();

        if (reimbursements.isEmpty()) {
            logger.info("\nReimbursements have been called with no reimbursements in the list!\n");
            ctx.json("There are currently no reimbursements that exist :)!");
        } else {
            logger.info("\nReimbursements have been displayed to screen\n");
            ctx.json(reimbursements);
        }
    };

    private final Handler getAllReimbursements = ctx ->  {

        logger.info("\nGet All Reimbursements Endpoint has been invoked." +
                "\nPath: " + ctx.path() +
                "\nHeader Names : " + ctx.res.getHeaderNames() +
                "\nIP Address : " + ctx.ip() +
                "\nUser Agent: " + ctx.userAgent() + "\n");

        // create a web token "Authorization" in header of the request
        // this will contain the token to be parsed
        String jwt = Objects.requireNonNull(ctx.header("Authorization")).split(" ")[1];

        // check token provided by end user
        Jws<Claims> token = this.jwtService.parseJWT(jwt);

        // validate tokens authority and if it's a Manager
        if (!token.getBody().get("user_role").equals("Manager")) {
            logger.info("\nAUTHORIZATION ATTACK HAS BEEN INITIATED! " +
                    "\nA user is attempting to access an endpoint greater than their set permissions! " +
                    "\nCurrent req method = " + ctx.req.getMethod() + "\n");
            // Unauthorized response = 401 status code
            throw new UnauthorizedResponse("You must be a trainer to access this point");
        }

        List<ResponseReimbursementDTO> reimbursements = this.reimbursementService.getAllReimbursements();

        if (reimbursements.isEmpty()) {
            logger.info("\nReimbursements have been called with no reimbursements in the list!\n");
            ctx.json("There are currently no reimbursements that exist :)!");
        } else {
            logger.info("\nReimbursements have been displayed to screen\n");
            ctx.json(reimbursements);
        }
    };

    private final Handler getAllReimbursementsOfUserX = ctx -> {
        String jwt = Objects.requireNonNull(ctx.header("Authorization")).split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJWT(jwt);

        if (!token.getBody().get("user_role").equals("Employee")) {
            throw new UnauthorizedResponse("You must be an employee to view this resource");
        }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if (!token.getBody().get("user_id").equals(id)) {
            logger.info("""

                  ALERT: POSSIBLE FRAUDULENT BEHAVIOR
                  An attempt to access an Unauthorized user account has been made by userID
                  """ + token.getBody().get("user_id"));
            throw new UnauthorizedResponse("You are not allowed to view this resource!");
        }

        List<ResponseReimbursementDTO> reimbursements = this.reimbursementService.getAllReimbursementsByUserId(id);

        if (reimbursements.isEmpty()) {
            logger.info("\nReimbursements have been called with no reimbursements in the list!\n");
            ctx.json("There are currently no reimbursements that exist :)!");
        } else {
            logger.info("\nReimbursements have been displayed to screen\n");
            ctx.json(reimbursements);
        }
    };
    private final Handler addReimbursement = ctx -> {
      String jwt = Objects.requireNonNull(ctx.header("Authorization")).split(" ")[1];

      Jws<Claims> token = this.jwtService.parseJWT(jwt);

      if (!token.getBody().get("user_role").equals("Employee")) {
          logger.info("""

                  ALERT: POSSIBLE FRAUDULENT BEHAVIOR
                  An attempt to create an Employee resource ADD REIMBURSEMENT
                  has been made by an external manager
                  """);
          throw new UnauthorizedResponse("You must be an employee to view this resource");
      }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if (!token.getBody().get("user_id").equals(id)) {
          logger.info("""

                  ALERT: POSSIBLE FRAUDULENT BEHAVIOR
                  An attempt to create an Employee resource ADD REIMBURSEMENT
                  has been made on another employee's reimbursements page by an UNAUTHORIZED employee
                  """);
          throw new UnauthorizedResponse("You are not allowed to reimburse this resource!");
      }

      String amount = ctx.formParam("amount");
      String type = ctx.formParam("type");
      String desc = "N/A";
      AddReimbursementDTO dto = new AddReimbursementDTO();
      dto.setDesc(desc);


      dto.setAmount(Integer.parseInt(amount));
      dto.setType(Integer.parseInt(type));

      dto.setAuthor(id);
      dto.setStatus(1);

      UploadedFile file = ctx.uploadedFile("receipt");
      if (!(file == null)) {
          InputStream is = file.getContent();
          dto.setReceipt(is);
      }

      ResponseReimbursementDTO getDTO = this.reimbursementService.addReimbursement(id, dto);
      ctx.status(201);
      ctx.json(getDTO);
    };

    private final Handler answerReimbursement = ctx -> {
        String jwt = Objects.requireNonNull(ctx.header("Authorization")).split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJWT(jwt);

        logger.info("\nAUTH Token has been successfully parsed and verified\n");

        if (!token.getBody().get("user_role").equals("Manager")) {
            logger.info("\nUNAUTHORIZED ACCESS ATTEMPTED BREACH!" +
                    "\nUSER id: " + token.getBody().get("user_id") +
                    "\nUSERNAME: " + token.getBody().get("username") +
                    "\nHas Attempted to answer a Reimbursement without proper Authorization!\n");
            throw new UnauthorizedResponse("You are not a manager cowboy!");
        }

        String reimbursementId = ctx.pathParam("reimbursement_id");
        int currentStatus = this.reimbursementService.getCurrentStatusByReimbursementId(reimbursementId);

        if (currentStatus != 1) {
            throw new UnauthorizedResponse("You cannot edit a reimbursement that has been fulfilled!");
        }

        String status = ctx.queryParam("status");
        int id = token.getBody().get("user_id", Integer.class);

        if (status == null || status.equals("1")) {
            logger.info("Status provided was NULL");
            throw new UnauthorizedResponse("You must specify 2 or 3");
        }

        ResponseReimbursementDTO responseDTO = this.reimbursementService.respondReimbursement(reimbursementId, status, id);
        ctx.json(responseDTO);
        logger.info("responseDTO has been displayed to screen. A reimbursement has just been fulfilled");
    };

    private final Handler getReimbursementReceipt = ctx -> {

        // potential extra security
//        String jwt = ctx.header("Authorization").split(" ")[1];
//        Jws<Claims> token = this.jwtService.parseJWT(jwt);

//        if (!(token.getBody().get("user_role").equals("Employee") || token.getBody().get("user_id").equals(userId))) {
//            throw new UnauthorizedResponse("You cannot access this Value");
//        }
//
//        if (token.getBody().get("user_role").equals("Employee") && !("" + token.getBody().get("user_id")).equals(userId)) {
//            throw new UnauthorizedResponse("You cannot access this Value, it is not your receipt!");
//        }

        String reimbId = ctx.pathParam("reimbursement_id");
        InputStream receipt = this.reimbursementService.getReimbursementReceipt(reimbId);

        Tika tika = new Tika();
        String mimeType = tika.detect(receipt);

        ctx.header("Content-type", mimeType);
        ctx.result(receipt);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.get("/pendingReimbursements", getAllUnfulfilledReimbursements);
        app.get("/users/{user_id}/reimbursements", getAllReimbursementsOfUserX);
        app.post("/users/{user_id}/reimbursements", addReimbursement);
        app.get("/reimbursements/{reimbursement_id}/receipt", getReimbursementReceipt);
        app.patch("/reimbursements/{reimbursement_id}", answerReimbursement);
    }
}
