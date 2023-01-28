/***************************************************************
 *
 * file: pss.h
 *
 * @Author  Nikolaos Vasilikopoulos (nvasilik@csd.uoc.gr)
 * @brief   Implementation of the "pss.h" header file for the Public Subscribe
 *System, function definitions
 *
 *
 ***************************************************************
 */
#include <stdio.h>
#include <stdlib.h>

#include "pss.h"

extern struct Group groups[MG];
extern struct SubInfo *sub;

int L_LookUP(struct Subscription *head, int id) {
  struct Subscription *current = head;
  while (current != NULL) {
    if (current->sId == id) {
      return 1;
    }
    current = current->snext;
  }
  return 0;
}
int L_Insert(struct Subscription **head, int id) {

  if (L_LookUP(*head, id)) {
    return -1;
  }
  struct Subscription *newGSub = malloc(sizeof(struct Subscription));
  newGSub->sId = id;
  if (*head == NULL) {
    *head = newGSub;
    newGSub->snext = NULL;
  } else {
    newGSub->snext = *head;
    *head = newGSub;
  }
  return 1;
}

int L_Delete(struct Subscription **head, int id) {
  if (*head == NULL) {
    return 0;
  } else {
    struct Subscription *prev, *current = *head;
    if (current->sId == id) {
      *head = current->snext;
      free(current);
      return 2;
    }
    while (current != NULL && current->sId != id) {
      prev = current;
      current = current->snext;
    }
    if (current == NULL)
      return 1;

    prev->snext = current->snext;
    current->snext = NULL;
    free(current);
  }
  return 2;
}

void L_Print(struct Subscription *head) {
  struct Subscription *current = head;
  printf("<");
  while (current != NULL) {
    (current->snext == NULL) ? printf("%d", current->sId)
                             : printf("%d,", current->sId);
    current = current->snext;
  }
  printf(">\n");
}

void printSL(struct SubInfo *head) {
  struct SubInfo *current = head;
  printf("<");
  while (current != NULL) {
    (current->snext == NULL) ? printf("%d", current->sId)
                             : printf("%d,", current->sId);
    current = current->snext;
  }
  printf(">\n");
}
struct SubInfo *SL_LookUp(struct SubInfo *head, int data) {
  struct SubInfo *current = head;
  while (current != NULL) {
    if (current->sId == data) {
      return current;
    }
    current = current->snext;
  }
  return NULL;
}
void SL_INSERT(struct SubInfo **head, struct SubInfo *new_sub) {
  struct SubInfo *temp = SL_LookUp(*head, new_sub->sId);
  if (temp != NULL) {
    printf("# Subscriber with Id %d already exists. #\n", new_sub->sId);
    free(new_sub);
    return;
  }

  if ((*head) == NULL || (*head)->stm >= new_sub->stm) {
    new_sub->snext = (*head);
    (*head) = new_sub;
  } else {
    struct SubInfo *current = (*head);
    while (current->snext != NULL && current->snext->stm < new_sub->stm) {
      current = current->snext;
    }
    new_sub->snext = current->snext;
    current->snext = new_sub;
  }
}

struct SubInfo *newSub(int id, int tm) {
  int i = 0;
  struct SubInfo *newNode = malloc(sizeof(struct SubInfo));
  newNode->sId = id;
  newNode->stm = tm;
  newNode->snext = NULL;
  for (i = 0; i < MG; i++) {
    newNode->sgp[i] = (struct Info *)1;
  }
  return newNode;
}

void SL_DELETE(struct SubInfo **head, int id) {
  int i = 0;
  struct SubInfo *current = *head, *prev;
  if (current != NULL && current->sId == id) {
    (*head) = current->snext;
    free(current);
    return;
  }
  while (current != NULL && current->sId != id) {
    prev = current;
    current = current->snext;
  }
  /* in case we there is no such subscriber*/
  if (current == NULL) {
    return;
  }
  /* in case there is:*/
  prev->snext = current->snext;
  free(current);
  return;
}

struct Info *DL_Lookup(struct Info *head, int id) {
  struct Info *current = head;

  while (current != NULL) {
    if (current->iId == id) {
      return current;
    }
    current = current->inext;
  }
  return NULL;
}

void DL_Print(struct Info *head) {
  struct Info *current = head;
  printf("<");
  while (current != NULL) {
    (current->inext == NULL) ? printf("%d", current->iId)
                             : printf("%d,", current->iId);
    current = current->inext;
  }
  printf(">");
}
int DL_Insert(struct Info **head, struct Info *new_info) {

  struct Info *temp = DL_Lookup(*head, new_info->iId);
  if (temp != NULL) {
    free(new_info);
    return 0;
  }

  /* If we the list is empty */
  if (*head == NULL) {
    *head = new_info;
    /* If our new info must be inserted in the first place of the list */
  } else if ((*head)->itm >= new_info->itm) {
    new_info->inext = *head;
    new_info->inext->iprev = new_info;
    *head = new_info;
    /* In any other case */
  } else {
    struct Info *current = *head;
    while (current->inext != NULL && current->inext->itm < new_info->itm) {
      current = current->inext;
    }
    new_info->inext = current->inext;

    /* If it is not inserted at the end */
    if (current->inext != NULL) {
      new_info->inext->iprev = new_info;
    }

    current->inext = new_info;
    new_info->iprev = current;
  }
  return 1;
}

struct Info *newInfo(int id, int tm) {

  int i = 0;
  struct Info *new_info = malloc(sizeof(struct Info));
  new_info->iId = id;
  new_info->itm = tm;
  new_info->inext = NULL;
  new_info->iprev = NULL;
  for (i = 0; i < MG; i++) {
    new_info->igp[i] = 0;
  }
  return new_info;
}

int DL_Delete(struct Info **head, int id) {
  if (*head == NULL)
    return 0;
  struct Info *current = *head;
  if (current->iId == id) {
    *head = current->inext;
    free(current);
    return 1;
  }
  while (current != NULL && current->iId != id) {
    current = current->inext;
  }

  if (current == NULL) {
    return 0;
  }
  if (current->inext != NULL) {
    current->iprev->inext = current->inext;
    current->inext->iprev = current->iprev;
    current->inext = NULL;
    current->iprev = NULL;
    free(current);
  } else {
    current->iprev->inext = current->inext;
    current->inext = NULL;
    current->iprev = NULL;
    free(current);
  }
  return 1;
}
/**
 * @brief Optional function to initialize data structures that
 *        need initialization
 *
 * @return 0 on success
 *         1 on failure
 */
int initialize(void) {
  int i = 0;
  for (i = 0; i < MG; i++) {
    groups[i].gId = i;
    groups[i].gfirst = NULL;
    groups[i].glast = NULL;
    groups[i].ggsub = NULL;
  }
  return EXIT_SUCCESS;
}

/**
 * @brief Free resources
 *
 * @return 0 on success
 *         1 on failure
 */
int free_all(void) {
  struct SubInfo *current = sub;
  struct Info *currentInfo = NULL;
  int sId = 0;
  int iId = 0;
  int i = 0;
  while (current != NULL) {
    sId = current->sId;
    for (i = 0; i < MG; i++) {
      L_Delete(&groups[i].ggsub, sId);
    }
    current = current->snext;
    SL_DELETE(&sub, sId);
  }
  for (i = 0; i < MG; i++) {
    currentInfo = groups[i].gfirst;
    while (currentInfo != NULL) {
      iId = currentInfo->iId;
      currentInfo = currentInfo->inext;
      DL_Delete(&groups[i].gfirst, iId);
    }
  }
  return EXIT_SUCCESS;
}

/**
 * @brief Insert info
 *
 * @param iTM Timestamp of arrival
 * @param iId Identifier of information
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Insert_Info(int iTM, int iId, int *gids_arr, int size_of_gids_arr) {

  int i = 0;
  int j = 0;
  for (i = 0; i < MG; i++) {
    if (DL_Lookup(groups[i].gfirst, iId) != NULL) {
      printf("# INFOID <%d> exists in GROUPID <%d> #\n", iId, i);
      return EXIT_FAILURE;
    }
  }
  for (j = 0; j < size_of_gids_arr - 1; j++) {
    struct Info *new_info = newInfo(iId, iTM);
    for (i = 0; i < size_of_gids_arr - 1; i++) {
      if (gids_arr[i] >= MG) {
        printf("# GROUPID = <%d> is WRONGinvalid. #\n", gids_arr[i]);
        free(new_info);
      } else {
        new_info->igp[gids_arr[i]] = 1;
      }
    }
    if (!(gids_arr[i] >= MG)) {
      if (DL_Insert(&groups[gids_arr[j]].gfirst, new_info) != 0) {
        struct Info *currentInfo = groups[gids_arr[j]].gfirst;
        while (currentInfo->inext != NULL) {
          currentInfo = currentInfo->inext;
        }
        groups[gids_arr[j]].glast = currentInfo;
      } else {
        printf("# Info_Id <%d> already exists in GROUPID = <%d> #\n", iId,
               groups[gids_arr[j]].gId);
        free(new_info);
      }
    }
  }
  return EXIT_SUCCESS;
}
/**
 * @brief Subsriber Registration
 *
 * @param sTM Timestamp of arrival
 * @param sId Identifier of subscriber
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Subscriber_Registration(int sTM, int sId, int *gids_arr,
                            int size_of_gids_arr) {

  int i = 0;
  int j = 0;
  struct SubInfo *new_sub = newSub(sId, sTM);
  for (j = 0; j < size_of_gids_arr - 1; j++) {
    for (i = 0; i < size_of_gids_arr - 1; i++) {
      if (gids_arr[i] >= MG) {
        printf("# GROUPID = <%d> is invalid. #\n", gids_arr[i]);
        free(new_sub);
      } else
        new_sub->sgp[gids_arr[i]] = groups[gids_arr[i]].gfirst;
    }
    /* Insert to the group list sub */
    if (!(gids_arr[j] >= MG))
      if (L_Insert(&groups[gids_arr[j]].ggsub, sId) == -1) {
        printf("# SubId <%d> already exists in GROUPID = <%d>. #\n", sId,
               groups[gids_arr[j]].gId);
      }
  }

  /* Insert to SubInfo list */
  SL_INSERT(&sub, new_sub);

  return EXIT_SUCCESS;
}
/**
 * @brief Consume Information for subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Consume(int sId) {
  int i = 0;
  int j = 0;

  struct SubInfo *consumeSub = SL_LookUp(sub, sId);
  /* if there is such Subscriber */
  if (consumeSub != NULL) {
    /* Re-assing the spg pointer in case the subscribtion occurs first and new
     * info enters the group */
    for (i = 0; i < MG; i++) {
      if (consumeSub->sgp[i] != (struct Info *)1 &&
          consumeSub->sgp[i] == NULL) {
        consumeSub->sgp[i] = groups[i].gfirst;
      }
    }
    struct Info *currentInfo = NULL;
    for (j = 0; j < MG; j++) {
      if (consumeSub->sgp[j] != NULL &&
          consumeSub->sgp[j] != (struct Info *)1) {
        currentInfo = consumeSub->sgp[j];
        printf("GROUPID = <%d>, INFOLIST = <", groups[j].gId);
        while (currentInfo->inext != NULL) {
          printf("%d, ", currentInfo->iId);
          currentInfo = currentInfo->inext;
        }
        printf("%d>, ", currentInfo->iId);
        consumeSub->sgp[j] = currentInfo;
        printf("NEWSGP = <%d>\n", consumeSub->sgp[j]->iId);
      }
    }
    return EXIT_SUCCESS;
  }
  return EXIT_FAILURE;
}
/**
 * @brief Delete subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Delete_Subscriber(int sId) {

  int i = 0;
  if (SL_LookUp(sub, sId)) {
    SL_DELETE(&sub, sId);
    printf("SUBSCRIBERLIST = ");
    printSL(sub);
    for (i = 0; i < MG; i++)
      if ((L_Delete(&groups[i].ggsub, sId) == 2)) {
        printf("GROUPID = <%d>, SUBLIST = ", groups[i].gId);
        L_Print(groups[i].ggsub);
      }
  } else {
    printf("# Subscriber with ID <%d> does not exists.\n", sId);
    return EXIT_FAILURE;
  }

  return EXIT_SUCCESS;
}
/**
 * @brief Print Data Structures of the system
 *
 * @return 0 on success
 *          1 on failure
 */
int Print_all(void) {

  printf("P DONE\n");
  int count = 0;
  int array[MG];
  int i = 0;
  int subCounter = 0;
  for (i = 0; i < MG; i++) {
    printf("GROUPID = <%d>, ", i);
    printf("INFOLIST = ");
    DL_Print(groups[i].gfirst);
    printf(", SUBLIST = ");
    L_Print(groups[i].ggsub);
  }
  printf("SUBSCRIBERLIST = ");
  printSL(sub);

  struct SubInfo *currentSub = sub;
  if (currentSub != NULL) {
    while (currentSub != NULL) {
      for (i = 0; i < MG; i++)
        array[i] = -1;
      count = 0;
      subCounter++;
      printf("SUBSCRIBERID = <%d>, ", currentSub->sId);
      printf("GROUPLIST = <");
      for (i = 0; i < MG; i++) {
        if (currentSub->sgp[i] != (struct Info *)1) {
          array[i] = i;
          count++;
        }
      }
      currentSub = currentSub->snext;
      for (i = 0; i < MG; i++) {
        if (array[i] != -1) {
          count--;
          (count == 0) ? printf("%d>\n", array[i]) : printf("%d,", array[i]);
        }
      }
    }
  }
  printf("NO_GROUPS = <64> ");
  printf("NO_SUBSCRIBERS = <%d>\n", subCounter);

  return EXIT_SUCCESS;
}
